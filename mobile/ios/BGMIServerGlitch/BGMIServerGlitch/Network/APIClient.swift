import Foundation
import Combine

class APIClient {
    static let shared = APIClient()
    
    private let baseURL: String
    private let session: URLSession
    
    init(baseURL: String = "http://your-backend-url/api/") {
        self.baseURL = baseURL
        
        let config = URLSessionConfiguration.default
        config.timeoutIntervalForRequest = 30
        config.timeoutIntervalForResource = 60
        self.session = URLSession(configuration: config)
    }
    
    // MARK: - Auth Endpoints
    
    func login(email: String, password: String) -> AnyPublisher<AuthResponse, Error> {
        let request = LoginRequest(email: email, password: password)
        return post(endpoint: "auth/login", body: request)
    }
    
    func register(username: String, email: String, password: String) -> AnyPublisher<AuthResponse, Error> {
        let request = RegisterRequest(username: username, email: email, password: password)
        return post(endpoint: "auth/register", body: request)
    }
    
    // MARK: - Server Access Endpoints
    
    func startServer(serverName: String, season: String, accessType: String) -> AnyPublisher<ServerAccess, Error> {
        let request = StartServerRequest(serverName: serverName, season: season, accessType: accessType)
        return post(endpoint: "server/start", body: request)
    }
    
    func getActiveAccess() -> AnyPublisher<[ServerAccess], Error> {
        return get(endpoint: "server/active")
    }
    
    func stopServer(accessId: Int) -> AnyPublisher<[String: String], Error> {
        return post(endpoint: "server/\(accessId)/stop", body: [String: String]())
    }
    
    // MARK: - Generic Request Methods
    
    private func get<T: Decodable>(endpoint: String) -> AnyPublisher<T, Error> {
        guard let url = URL(string: baseURL + endpoint) else {
            return Fail(error: URLError(.badURL)).eraseToAnyPublisher()
        }
        
        var request = URLRequest(url: url)
        addHeaders(&request)
        
        return session.dataTaskPublisher(for: request)
            .mapError { $0 as Error }
            .decode(type: T.self, decoder: JSONDecoder())
            .eraseToAnyPublisher()
    }
    
    private func post<T: Decodable, U: Encodable>(endpoint: String, body: U) -> AnyPublisher<T, Error> {
        guard let url = URL(string: baseURL + endpoint) else {
            return Fail(error: URLError(.badURL)).eraseToAnyPublisher()
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        addHeaders(&request)
        
        do {
            request.httpBody = try JSONEncoder().encode(body)
        } catch {
            return Fail(error: error).eraseToAnyPublisher()
        }
        
        return session.dataTaskPublisher(for: request)
            .mapError { $0 as Error }
            .decode(type: T.self, decoder: JSONDecoder())
            .eraseToAnyPublisher()
    }
    
    private func addHeaders(_ request: inout URLRequest) {
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        if let token = APIConfig.shared.authToken {
            request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        }
    }
}
