import SwiftUI

struct LoginView: View {
    @Binding var isLoggedIn: Bool
    @StateObject var viewModel: AuthViewModel
    
    @State private var email = ""
    @State private var password = ""
    @State private var showRegister = false
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20) {
                Text("BGMI Server Glitch")
                    .font(.title)
                    .fontWeight(.bold)
                    .padding(.top, 40)
                
                VStack(spacing: 16) {
                    TextField("Email", text: $email)
                        .textFieldStyle(.roundedBorder)
                        .keyboardType(.emailAddress)
                        .autocapitalization(.none)
                    
                    SecureField("Password", text: $password)
                        .textFieldStyle(.roundedBorder)
                }
                .padding(.horizontal, 20)
                .padding(.top, 20)
                
                if let errorMessage = viewModel.errorMessage {
                    Text(errorMessage)
                        .foregroundColor(.red)
                        .font(.caption)
                        .padding(.horizontal, 20)
                }
                
                Button(action: handleLogin) {
                    if viewModel.isLoading {
                        ProgressView()
                            .tint(.white)
                    } else {
                        Text("Login")
                    }
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 12)
                .background(Color.blue)
                .foregroundColor(.white)
                .cornerRadius(8)
                .padding(.horizontal, 20)
                .disabled(viewModel.isLoading || email.isEmpty || password.isEmpty)
                
                Divider()
                    .padding(.horizontal, 20)
                
                NavigationLink(destination: RegisterView(isLoggedIn: $isLoggedIn)) {
                    Text("Don't have an account? Register")
                        .foregroundColor(.blue)
                }
                
                Spacer()
            }
            .navigationBarHidden(true)
        }
    }
    
    private func handleLogin() {
        viewModel.login(email: email, password: password)
        if viewModel.user != nil {
            isLoggedIn = true
        }
    }
}

#Preview {
    LoginView(isLoggedIn: .constant(false), viewModel: AuthViewModel())
}
