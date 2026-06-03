import Foundation

struct ServerAccess: Codable, Identifiable {
    let id: Int
    let serverName: String
    let remainingTimeMillis: Int
    let expiresAt: String
    let status: String
    let message: String

    enum CodingKeys: String, CodingKey {
        case id
        case serverName
        case remainingTimeMillis
        case expiresAt
        case status
        case message
    }
}
