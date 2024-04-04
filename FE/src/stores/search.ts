import { create } from "zustand";
import { persist } from "zustand/middleware";

interface SearchState {
  keyword?: string;

  setKeyword: (keyword: string) => void;
  clearKeyword: () => void;
}

const useKeywordStore = create<SearchState>()(
  // persist를 통해 localStorage에 로그인 정보 저장
  persist(
    (set) => ({
      setKeyword: (keyword: string) => {
        set({
          keyword: keyword,
        });
      },
      clearKeyword: () => {
        set({
          keyword: undefined,
        });
      },
    }),
    {
      name: "keyword-storage",
    },
  ),
);

export { useKeywordStore };
