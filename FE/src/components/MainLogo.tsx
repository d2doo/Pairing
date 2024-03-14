import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

function MainLogo() {
  return (
    <>
      <div className="flex justify-center items-center my-6">
        <img src="img/logo.png" alt="logo_err" className="w-2/3" />
      </div>
      <div className="flex justify-center">
        <div
          className="
        flex items-center w-1/2 place-content-around rounded-full
        box-border 
        border-solid
        border-2 
        border-blue1
        px-4
        "
        >
          <Input
            type="search"
            className="box-border border-none flex-grow h-11 font-['GothicOTFMedium'] text-lg"
            placeholder="찾고있는 물건을 입력해보세요."
          />
          <Button type="submit" className="ml-4 font-['GothicOTFLight']">
            검색
          </Button>
        </div>
      </div>
    </>
  );
}

export default MainLogo;
