import Products from "@/components/ProductTypeR";

function InfoSale() {
  return (
    <>
      <div className="flex w-full flex-col items-center font-Gothic">
        <div className="m-10 w-2/3 text-sm">
          <p>
            <span className="font-GothicBold">d2doo</span>님의 거래 신뢰도는
          </p>
          <p>
            <span className="font-GothicBold">30/100</span> 입니다.
          </p>
          <p className="my-4">오늘도 즐거운 쇼핑되세요 :D</p>
        </div>
        <div className="border-y py-6">
          <Products />
        </div>
      </div>
    </>
  );
}

export default InfoSale;
