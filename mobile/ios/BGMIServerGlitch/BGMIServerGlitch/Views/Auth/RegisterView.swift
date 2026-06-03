import SwiftUI

struct RegisterView: View {
    @Binding var isLoggedIn: Bool
    @StateObject var viewModel: AuthViewModel
    @Environment(\.presentationMode) var presentationMode
    
    @State private var username = ""
    @State private var email = ""
    @State private var password = ""
    @State private var confirmPassword = ""
    
    var body: some View {
        VStack(spacing: 20) {
            Text("Create Account")
                .font(.title)
                .fontWeight(.bold)
                .padding(.top, 20)
            
            VStack(spacing: 16) {
                TextField("Username", text: $username)
                    .textFieldStyle(.roundedBorder)
                    .autocapitalization(.none)
                
                TextField("Email", text: $email)
                    .textFieldStyle(.roundedBorder)
                    .keyboardType(.emailAddress)
                    .autocapitalization(.none)
                
                SecureField("Password", text: $password)
                    .textFieldStyle(.roundedBorder)
                
                SecureField("Confirm Password", text: $confirmPassword)
                    .textFieldStyle(.roundedBorder)
            }
            .padding(.horizontal, 20)
            
            if let errorMessage = viewModel.errorMessage {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .font(.caption)
                    .padding(.horizontal, 20)
            }
            
            Button(action: handleRegister) {
                if viewModel.isLoading {
                    ProgressView()
                        .tint(.white)
                } else {
                    Text("Register")
                }
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 12)
            .background(Color.green)
            .foregroundColor(.white)
            .cornerRadius(8)
            .padding(.horizontal, 20)
            .disabled(viewModel.isLoading || !isFormValid())
            
            Spacer()
        }
        .navigationBarTitleDisplayMode(.inline)
    }
    
    private func handleRegister() {
        guard password == confirmPassword else {
            viewModel.errorMessage = "Passwords do not match"
            return
        }
        
        viewModel.register(username: username, email: email, password: password)
        if viewModel.user != nil {
            isLoggedIn = true
        }
    }
    
    private func isFormValid() -> Bool {
        !username.isEmpty && !email.isEmpty && !password.isEmpty && !confirmPassword.isEmpty
    }
}

#Preview {
    RegisterView(isLoggedIn: .constant(false), viewModel: AuthViewModel())
}
