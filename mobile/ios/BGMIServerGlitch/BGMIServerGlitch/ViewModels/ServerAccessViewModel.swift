import Foundation
import Combine

class ServerAccessViewModel: ObservableObject {
    @Published var activeAccess: [ServerAccess] = []
    @Published var remainingTime: Int = 0
    @Published var isLoading = false
    @Published var errorMessage: String?
    @Published var timerRunning = false
    
    private var cancellables = Set<AnyCancellable>()
    private let apiClient = APIClient.shared
    private var timer: Timer?
    
    func startServer(serverName: String, season: String, accessType: String = "FREE_TRIAL") {
        isLoading = true
        errorMessage = nil
        
        apiClient.startServer(serverName: serverName, season: season, accessType: accessType)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] access in
                self?.remainingTime = access.remainingTimeMillis / 1000
                self?.startTimer()
                self?.fetchActiveAccess()
            }
            .store(in: &cancellables)
    }
    
    func fetchActiveAccess() {
        apiClient.getActiveAccess()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] accesses in
                self?.activeAccess = accesses
            }
            .store(in: &cancellables)
    }
    
    func stopServer(accessId: Int) {
        apiClient.stopServer(accessId: accessId)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] _ in
                self?.fetchActiveAccess()
            }
            .store(in: &cancellables)
    }
    
    private func startTimer() {
        timerRunning = true
        timer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { [weak self] _ in
            if self?.remainingTime ?? 0 > 0 {
                self?.remainingTime -= 1
            } else {
                self?.stopTimer()
            }
        }
    }
    
    private func stopTimer() {
        timer?.invalidate()
        timer = nil
        timerRunning = false
        remainingTime = 0
    }
    
    deinit {
        stopTimer()
    }
}
