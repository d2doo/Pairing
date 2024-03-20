import SearchBar from "@/components/SearchBar";
import Banner from "@/components/banner";
import RecentProductTitle from "@/components/RecentProductTitle";
import BottomNavigationBar from "@/components/BottomNavigationBar";

function MainPage() {
  return (
    <>
      <SearchBar />
      <Banner />
      <RecentProductTitle />
      <BottomNavigationBar />
    </>
  );
}

export default MainPage;
