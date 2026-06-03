import SwiftUI

@main
struct BGMIServerGlitchApp: App {
    @State private var isLoggedIn = false
    @StateObject var authViewModel = AuthViewModel()

    var body: some Scene {
        WindowGroup {
            if isLoggedIn {
                DashboardView(isLoggedIn: $isLoggedIn)
                    .environmentObject(authViewModel)
            } else {
                LoginView(isLoggedIn: $isLoggedIn)
                    .environmentObject(authViewModel)
            }
        }
    }
}
