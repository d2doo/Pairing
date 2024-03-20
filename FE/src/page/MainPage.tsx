import SearchBar from "@/components/SearchBar";
import Banner from "@/components/banner";
import RecentProductTitle from "@/components/RecentProductTitle";
import BottomNavigationBar from "@/components/Bottom-Navigation-Bar";

function MainPage() {
  return (
    <>
      <SearchBar />
      <Banner />
      <BottomNavigationBar />
      <RecentProductTitle />
    </>
  );
}

export default MainPage;
