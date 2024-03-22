import { Outlet } from "react-router-dom";
import BottomNavigationBar from "@/components/BottomNavigationBar";

function SalePage() {
  return (
    <>
      {/* <div className="pb-14"> */}
      <Outlet />
      {/* </div> */}
      <BottomNavigationBar />
    </>
  );
}

export default SalePage;
