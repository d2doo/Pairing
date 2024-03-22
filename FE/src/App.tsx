import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./page/LoginPage";
import MainPage from "./page/MainPage";
import ChatPage from "./page/ChatPage";
import ChatRoom from "./page/ChatRoomPage";
import ChatList from "./components/ChatList";
import SalePage from "./page/SalePage";
import SaleMain from "./components/SaleMain";
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
        </Routes>
      </BrowserRouter>
      {/* Routing 정의 끝 */}
    </>
  );
}

export default App;
