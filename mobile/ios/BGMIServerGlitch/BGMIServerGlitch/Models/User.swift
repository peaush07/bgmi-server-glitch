import Foundation

struct User: Codable, Identifiable {
    let id: Int
    let username: String
    let email: String
    let role: String
}
