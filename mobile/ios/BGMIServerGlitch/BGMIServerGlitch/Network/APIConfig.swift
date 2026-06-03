import Foundation

class APIConfig {
    static let shared = APIConfig()
    
    let baseURL = "http://your-backend-url/api/"
    
    var authToken: String? {
        get {
            UserDefaults.standard.string(forKey: "authToken")
        }
        set {
            if let value = newValue {
                UserDefaults.standard.set(value, forKey: "authToken")
            } else {
                UserDefaults.standard.removeObject(forKey: "authToken")
            }
        }
    }
    
    var userId: Int? {
        get {
            UserDefaults.standard.integer(forKey: "userId")
        }
        set {
            if let value = newValue {
                UserDefaults.standard.set(value, forKey: "userId")
            }
        }
    }
}
