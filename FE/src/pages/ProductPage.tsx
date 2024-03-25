import { Outlet } from "react-router-dom";
import Header from "@/components/Header";
import BottomNavigationBar from "@/components/BottomNavigationBar";

function ProductPage() {
  return (
    <>
      <Header />
      <div className="pb-14">
        <Outlet />
      </div>
      <BottomNavigationBar />
    </>
  );
}

export default ProductPage;
