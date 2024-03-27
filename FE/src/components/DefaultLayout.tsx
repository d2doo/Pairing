import { Outlet } from "react-router-dom";
import Header from "@/components/Header";
import SearchBar from "@/components/SearchBar";
import BottomNavigationBar from "./BottomNavigationBar";

interface layoutProps {
  headerType?: "searchBar" | "titleBar";
  hideFooter?: boolean;
}

export default function DefaultLayout(props: layoutProps) {
  return (
    <>
      {props.headerType === "searchBar" ? (
        <SearchBar />
      ) : props.headerType === "titleBar" ? (
        <Header />
      ) : (
        <></>
      )}
      <div className="flex w-full flex-1 flex-col overflow-y-scroll">
        <Outlet />
      </div>
      {props.hideFooter ? <></> : <BottomNavigationBar />}
    </>
  );
}
