import SwiftUI

struct DashboardView: View {
    @Binding var isLoggedIn: Bool
    @StateObject var serverViewModel = ServerAccessViewModel()
    @EnvironmentObject var authViewModel: AuthViewModel
    
    @State private var serverName = ""
    @State private var selectedSeason = "Season 1"
    @State private var showTimer = false
    
    let seasons = ["Season 1", "Season 2", "Season 3", "Season 4"]
    
    var body: some View {
        NavigationView {
            VStack(spacing: 16) {
                // Header
                HStack {
                    Text("Server Glitch")
                        .font(.title2)
                        .fontWeight(.bold)
                    Spacer()
                    Button(action: { authViewModel.logout(); isLoggedIn = false }) {
                        Text("Logout")
                            .font(.caption)
                    }
                }
                .padding(.horizontal, 16)
                .padding(.top, 12)
                
                // Server Input Section
                VStack(spacing: 12) {
                    TextField("Server Name", text: $serverName)
                        .textFieldStyle(.roundedBorder)
                    
                    Picker("Season", selection: $selectedSeason) {
                        ForEach(seasons, id: \.self) { season in
                            Text(season).tag(season)
                        }
                    }
                    .pickerStyle(.segmented)
                    
                    Button(action: startServer) {
                        if serverViewModel.isLoading {
                            ProgressView()
                                .tint(.white)
                        } else {
                            Text("Start Free Trial (5 min)")
                                .fontWeight(.semibold)
                        }
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
                    .disabled(serverViewModel.isLoading || serverName.isEmpty)
                }
                .padding(16)
                .background(Color(.systemGray6))
                .cornerRadius(12)
                .padding(.horizontal, 16)
                
                // Timer Display
                if serverViewModel.timerRunning {
                    VStack {
                        Text(timeString(seconds: serverViewModel.remainingTime))
                            .font(.system(size: 48, weight: .bold, design: .monospaced))
                            .foregroundColor(.blue)
                        Text("Time Remaining")
                            .font(.caption)
                            .foregroundColor(.gray)
                    }
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color(.systemGray6))
                    .cornerRadius(12)
                    .padding(.horizontal, 16)
                }
                
                // Active Sessions
                if !serverViewModel.activeAccess.isEmpty {
                    Text("Active Sessions")
                        .font(.headline)
                        .padding(.horizontal, 16)
                    
                    List(serverViewModel.activeAccess) { access in
                        VStack(alignment: .leading, spacing: 4) {
                            Text(access.serverName)
                                .font(.headline)
                            Text(access.status)
                                .font(.caption)
                                .foregroundColor(.gray)
                            Button(action: { serverViewModel.stopServer(accessId: access.id) }) {
                                Text("Stop")
                                    .foregroundColor(.red)
                            }
                        }
                    }
                    .frame(maxHeight: 300)
                }
                
                if let errorMessage = serverViewModel.errorMessage {
                    Text(errorMessage)
                        .foregroundColor(.red)
                        .font(.caption)
                        .padding(.horizontal, 16)
                }
                
                Spacer()
            }
            .navigationBarHidden(true)
            .onAppear {
                serverViewModel.fetchActiveAccess()
            }
        }
    }
    
    private func startServer() {
        serverViewModel.startServer(serverName: serverName, season: selectedSeason)
    }
    
    private func timeString(seconds: Int) -> String {
        let minutes = seconds / 60
        let secs = seconds % 60
        return String(format: "%02d:%02d", minutes, secs)
    }
}

#Preview {
    DashboardView(isLoggedIn: .constant(true))
}
