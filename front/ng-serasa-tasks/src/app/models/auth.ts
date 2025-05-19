import { Profile } from "./profile";

export interface LoginData {
    email: string;
    password: string;
}

export interface SignUpData {
    fullName: string;
    email: string;
    password: string;
    confirmPassword: string;
    avatarUrl: string;
}

export interface JwtAuthToken {
    accessToken: string;
    refreshToken: string;
}

export interface AuthResponse { 
    profile: Profile, 
    accessToken: string 
}