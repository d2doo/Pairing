import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import MainPage from "@/pages/MainPage";
import ChatPage from "@/pages/ChatPage";
import ChatRoom from "@/pages/ChatRoomPage";
import ChatList from "./components/ChatList";
import SalePage from "./page/SalePage";
import SaleMain from "./components/SaleMain";
import {KakaoAuthCallback} from "@/pages/auth/KakaoAuthCallback.tsx";
function App() {
  return (
    <>
      {/* Routing 정의 시작 */}
      <BrowserRouter>
        <Routes>
          <Route element={<SalePage />}>
            <Route path="/new" element={<SaleMain />} />
            {/* 개별 상품 등록 route */}
            {/* <Route path="/new/unit" element={<컴포넌트 넣어라. />} /> */}
          </Route>
          <Route element={<ChatPage />}>
            <Route path="/chat" element={<ChatList />} />
            <Route path="/chat/room" element={<ChatRoom />} />
          </Route>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<LoginPage />} />

          <Route path="/auth/kakao" element={<KakaoAuthCallback />} />
        </Routes>
      </BrowserRouter>
      {/* Routing 정의 끝 */}
    </>
  );
}

export default App;
