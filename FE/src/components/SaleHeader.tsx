import { Outlet } from "react-router-dom";
import Header from "./Header";

function SaleHeader() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}

export default SaleHeader;
