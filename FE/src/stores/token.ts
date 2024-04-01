import { create } from "zustand";

interface TokenState {
    accessToken?: string;
    setAccessToken: (accessToken: string) => void;
    clearAccessToken: () => void;
}

const useTokenStore = create<TokenState>()(
    (set) => ({
        setAccessToken: (accessToken: string) => {
            set({
                accessToken
            });
        },
        clearAccessToken: () => {
            set({
                accessToken: undefined
            });
        }
    })
);

export { useTokenStore };
