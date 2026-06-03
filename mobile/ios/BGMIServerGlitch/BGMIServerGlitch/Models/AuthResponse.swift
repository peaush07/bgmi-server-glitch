import Foundation

struct AuthResponse: Codable {
    let token: String
    let message: String
    let userId: Int
    let username: String

    enum CodingKeys: String, CodingKey {
        case token
        case message
        case userId = "userId"
        case username
    }
}
