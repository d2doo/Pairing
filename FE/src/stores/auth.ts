import { create } from 'zustand';
import {MemberLoginResponse, MemberSummaryResponse} from "@/types/Member.ts";

interface AuthState {
    accessToken?: string;
    memberId?: string;
    nickname?: string;
    profileImage?: string;
    score?: number;

    setAuth: (response: MemberLoginResponse) => void;
    setAccessToken: (accessToken: string) => void;
    clearAuth: () => void;
}

const useAuthStore = create<AuthState>(
    // persist를 통해 localStorage에 로그인 정보 저장
    (set) => ({
        setAuth: (response: MemberLoginResponse) => {
            set({
                accessToken: response.accessToken,
                memberId: response.memberInfo.memberId,
                nickname: response.memberInfo.nickname,
                profileImage: response.memberInfo.profileImage,
                score: response.memberInfo.score
            });
        },
        setAccessToken: (accessToken: string) => {
            set({
                accessToken
            });
        },
        clearAuth: () => {
            set({
                accessToken: undefined,
                memberId: undefined,
                nickname: undefined,
                profileImage: undefined,
                score: undefined
            })
        },
    })
);

export { useAuthStore };
