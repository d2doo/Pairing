import SearchBar from "@/components/SearchBar";
import Banner from "@/components/Banner";
import ProductTypeC from "@/components/ProductTypeC";
import RecentProductTitle from "@/components/RecentProductTitle";
import BottomNavigationBar from "@/components/BottomNavigationBar";

function MainPage() {
  return (
    <>
      <SearchBar />
      <Banner />
      <RecentProductTitle />
      <div className="pb-14">
        <ProductTypeC />
      </div>
      <BottomNavigationBar />
    </>
  );
}

export default MainPage;
