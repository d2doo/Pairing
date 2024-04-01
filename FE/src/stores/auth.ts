import { create } from "zustand";
import { persist } from "zustand/middleware";

import {
    MemberLoginResponse,
    MemberResponse, MemberSummaryResponse,
    //   MemberSummaryResponse,
} from "@/types/Member.ts";

interface AuthState {
  memberId?: string;
  nickname?: string;
  profileImage?: string;
  score?: number;

  setAuth: (response: MemberSummaryResponse) => void;
  setAuthByChange: (response: MemberResponse) => void;
  clearAuth: () => void;
}

const useAuthStore = create<AuthState>()(
  // persist를 통해 localStorage에 로그인 정보 저장
  persist(
    (set) => ({
      setAuth: (response: MemberSummaryResponse) => {
        set({
          memberId: response.memberId,
          nickname: response.nickname,
          profileImage: response.profileImage,
          score: response.score,
        });
      },
      setAuthByChange: (response: MemberResponse) => {
        set({
          memberId: response.memberId,
          nickname: response.nickname,
          profileImage: response.profileImage,
          score: response.score,
        });
      },
      clearAuth: () => {
        set({
          memberId: undefined,
          nickname: undefined,
          profileImage: undefined,
          score: undefined,
        });
      },
    }), {
      name: 'auth-storage'
    }
  )
);

export { useAuthStore };
