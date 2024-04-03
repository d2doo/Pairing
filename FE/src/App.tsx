import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import ChatRoom from "@/pages/ChatRoomPage";
import ChatList from "@/components/ChatList";
import SaleMain from "@/components/SaleMain";
import ProductDetailPage from "@/pages/ProductDetailPage";
import ProductListPage from "@/pages/ProductListPage";
import { KakaoAuthCallback } from "@/pages/auth/KakaoAuthCallback.tsx";
import UnitLists from "@/components/SaleUnitLists";
import SaleProduct from "@/components/SaleProduct";
import MyPage from "@/pages/MyPage";
import DefaultLayout from "./components/DefaultLayout";
import SaleUnit from "./components/SaleUnit";
import { AuthRoute } from "@/components/AuthRoute.tsx";
import { useEffect } from "react";
import { useTokenStore } from "@/stores/token.ts";
import axios from "axios";
import { useAuthStore } from "@/stores/auth.ts";
import { GoogleAuthCallback } from "@/pages/auth/GoogleAuthCallback.tsx";
import SearchPage from "./pages/SearchPage";

function App() {
  const authStore = useAuthStore();
  const tokenStore = useTokenStore();

  useEffect(() => {
    if (!tokenStore.accessToken) {
      axios
        .post<{ accessToken: string }>(
          import.meta.env.VITE_API_BASE_URL + "/refresh",
          null,
          {
            withCredentials: true,
          },
        )
        .then((response) => {
          tokenStore.setAccessToken(response.data.accessToken);
        })
        .catch((error) => {
          tokenStore.clearAccessToken();
          authStore.clearAuth();
        });
    }
  }, []);

  return (
    <div className="relative flex h-[100dvh] flex-col overflow-y-hidden">
      {/* Routing 정의 시작 */}
      <BrowserRouter>
        <Routes>
          {/* 로그인이 필요한 페이지 */}
          <Route element={<AuthRoute />}>
            <Route element={<DefaultLayout headerType="titleBar" />}>
              <Route path="/new/unit-lists" element={<UnitLists />} />
              <Route path="/new/product" element={<SaleProduct />} />
              <Route path="/new/unit" element={<SaleUnit />} />
            </Route>
            <Route element={<DefaultLayout headerType="titleBar" />}>
              <Route path="/mypage" element={<MyPage />} />
              <Route path="/chat" element={<ChatList />} />
              <Route path="/chat/room/:roomId" element={<ChatRoom />} />
            </Route>
          </Route>

          {/* 로그인이 필요없는 페이지 */}
          <Route element={<DefaultLayout hideFooter={true} />}>
            <Route path="/login" element={<LoginPage />} />
          </Route>
          <Route element={<DefaultLayout headerType="titleBar" />}>
            {/* <Route element={<DefaultLayout />}> */}
            <Route path="/product/:id" element={<ProductDetailPage />} />
            <Route path="/auth/kakao" element={<KakaoAuthCallback />} />
            <Route path="/auth/google" element={<GoogleAuthCallback />} />
          </Route>
          <Route element={<DefaultLayout headerType="searchBar" />}>
            {/* <Route element={<DefaultLayout headerType={true} />}> */}
            <Route path="/category" element={<ProductListPage />} />
          </Route>
          <Route element={<DefaultLayout />}>
            <Route path="/" element={<SaleMain />} />
          </Route>
          <Route path="/search" element={<SearchPage />} />
        </Routes>
      </BrowserRouter>
      {/* Routing 정의 끝 */}
    </div>
  );
}

export default App;
