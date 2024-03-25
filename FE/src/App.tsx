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
import FindUnit from "@/components/SaleFindUnit";
import MyPage from "@/pages/MyPage";
import DefaultLayout from "./components/DefaultLayout";

function App() {
  return (
    <div className="relative flex h-[100vh] flex-col overflow-y-hidden">
      {/* Routing 정의 시작 */}
      <BrowserRouter>
        <Routes>
          <Route element={<DefaultLayout />}>
            <Route path="/new" element={<SaleMain />} />
          </Route>
          <Route element={<DefaultLayout headerType="titleBar" />}>
            <Route path="/new/unit-lists" element={<UnitLists />} />
            <Route path="/new/product" element={<FindUnit />} />
          </Route>
          <Route element={<DefaultLayout hideFooter={true} />}>
            <Route path="/login" element={<LoginPage />} />
          </Route>
          <Route element={<DefaultLayout headerType="titleBar" />}>
            <Route path="/auth/kakao" element={<KakaoAuthCallback />} />
            <Route path="/mypage" element={<MyPage />} />
            <Route path="/chat" element={<ChatList />} />
            <Route path="/chat/room" element={<ChatRoom />} />
            <Route path="/product/:id" element={<ProductDetailPage />} />
          </Route>
          <Route element={<DefaultLayout headerType="searchBar" />}>
            <Route path="/category" element={<ProductListPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
      {/* Routing 정의 끝 */}
    </div>
  );
}

export default App;
