import { Outlet, useLocation } from "react-router-dom";
import Header from "@/components/Header";
import SearchBar from "@/components/SearchBar";
import BottomNavigationBar from "./BottomNavigationBar";
import { useState, useEffect } from "react";

interface layoutProps {
  headerType?: "searchBar" | "titleBar";
  hideFooter?: boolean;
}

export default function DefaultLayout(props: layoutProps) {
  const location = useLocation();
  const [headerProps, setHeaderProps] = useState({ title: "", prev: "" });

  useEffect(() => {
    if (location.pathname.match(/^\/product\/\d+$/)) {
      setHeaderProps({ title: "상품조회", prev: "/category" });
    } else if (location.pathname === "/new/product") {
      setHeaderProps({ title: "상품등록", prev: "/new/unit" });
    } else if (location.pathname === "/new/unit") {
      setHeaderProps({ title: "상품등록", prev: "/" });
    } else if (location.pathname === "/mypage") {
      setHeaderProps({ title: "마이페이지", prev: "/" });
    } else if (location.pathname === "/chat") {
      setHeaderProps({ title: "채팅", prev: "/" });
    } else if (location.pathname.match(/^\/chat\/room\/\d+$/)) {
      setHeaderProps({ title: "채팅", prev: "/chat" });
    }
  }, [location]);

  return (
    <>
      {props.headerType === "searchBar" ? (
        <SearchBar />
      ) : props.headerType === "titleBar" ? (
        <Header title={headerProps.title} prev={headerProps.prev} />
      ) : (
        <></>
      )}
      <div className="flex w-full flex-1 flex-col overflow-auto">
        <Outlet />
      </div>
      {props.hideFooter ? <></> : <BottomNavigationBar />}
    </>
  );
}
