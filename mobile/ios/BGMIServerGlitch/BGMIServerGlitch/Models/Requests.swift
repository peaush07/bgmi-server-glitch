import Foundation

struct LoginRequest: Codable {
    let email: String
    let password: String
}

struct RegisterRequest: Codable {
    let username: String
    let email: String
    let password: String
}

struct StartServerRequest: Codable {
    let serverName: String
    let season: String
    let accessType: String
}
