import { create } from 'zustand';
import { authApi } from '../api/client';
import type { User } from '../types';

interface AuthState {
  user: User | null;
  token: string | null;
  loading: boolean;
  error: string | null;
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, password: string, fullName: string) => Promise<void>;
  logout: () => void;
  initializeAuth: () => void;
  clearError: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  token: null,
  loading: false,
  error: null,

  initializeAuth: () => {
    const token = localStorage.getItem('token');
    const userRaw = localStorage.getItem('user');
    if (token && userRaw) {
      try {
        const user = JSON.parse(userRaw);
        set({ user, token });
      } catch {}
    }
  },

  login: async (email, password) => {
    set({ loading: true, error: null });
    try {
      const res = await authApi.login(email, password);
      const { token, userId, email: userEmail, fullName, role } = res.data;
      const user: User = { id: userId, email: userEmail, fullName, role };
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      localStorage.setItem('user', JSON.stringify(user));
      set({ user, token, loading: false });
    } catch (err: any) {
      const message = err.response?.data?.error || 'Login failed. Please try again.';
      set({ error: message, loading: false });
    }
  },

  register: async (email, password, fullName) => {
    set({ loading: true, error: null });
    try {
      const res = await authApi.register(email, password, fullName);
      const { token, userId, email: userEmail, role } = res.data;
      const user: User = { id: userId, email: userEmail, fullName, role };
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      localStorage.setItem('user', JSON.stringify(user));
      set({ user, token, loading: false });
    } catch (err: any) {
      const message = err.response?.data?.error || 'Registration failed. Please try again.';
      set({ error: message, loading: false });
    }
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('user');
    set({ user: null, token: null });
  },

  clearError: () => set({ error: null }),
}));
