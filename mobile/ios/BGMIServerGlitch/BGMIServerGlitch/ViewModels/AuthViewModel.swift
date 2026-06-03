import Foundation
import Combine

class AuthViewModel: ObservableObject {
    @Published var isLoading = false
    @Published var errorMessage: String?
    @Published var user: User?
    
    private var cancellables = Set<AnyCancellable>()
    private let apiClient = APIClient.shared
    
    func login(email: String, password: String) {
        isLoading = true
        errorMessage = nil
        
        apiClient.login(email: email, password: password)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] response in
                self?.saveAuthResponse(response)
            }
            .store(in: &cancellables)
    }
    
    func register(username: String, email: String, password: String) {
        isLoading = true
        errorMessage = nil
        
        apiClient.register(username: username, email: email, password: password)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] response in
                self?.saveAuthResponse(response)
            }
            .store(in: &cancellables)
    }
    
    func logout() {
        APIConfig.shared.authToken = nil
        APIConfig.shared.userId = nil
        user = nil
    }
    
    private func saveAuthResponse(_ response: AuthResponse) {
        APIConfig.shared.authToken = response.token
        APIConfig.shared.userId = response.userId
        user = User(
            id: response.userId,
            username: response.username,
            email: "",
            role: "user"
        )
    }
}
