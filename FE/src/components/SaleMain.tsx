import { Link } from "react-router-dom";

function SalePage() {
  return (
    <>
      {/* 팝니다 */}
      <Link
        to={"/new/unit"}
        className="flex h-1/2 justify-evenly bg-white1 p-10"
      >
        <img
          src="/img/extra-product-img.png"
          alt="product_img_err"
          className="w-1/2 animate-doong-sil object-cover"
        />
        <div className="mt-auto flex h-full flex-col justify-end space-y-5 py-5 font-GothicLight">
          <div className="w-3/4 border-b-2 border-blue1">
            <p className="font-GothicLight text-xs">pairing mine!</p>
            <p className="font-GothicMedium text-xl">내 상품 등록</p>
          </div>
          <div className="space-y-1">
            <p className="text-xs">
              <p>내 상품을 등록하고 조립해서</p>
              <p>판매해보세요!</p>
            </p>
            <p className="text-md font-Gothic">상품 등록하러 가기 →</p>
          </div>
        </div>
      </Link>

      {/* 삽니다 */}
      <Link to={"/category"} className="h-1/2 bg-blue1 p-10">
        <div className="flex h-full justify-evenly space-x-1">
          <div className="mt-auto flex h-full flex-col items-end justify-end space-y-5 py-5 font-GothicLight">
            <div className="w-2/3 border-b-2 border-white1 text-end">
              <p className="font-GothicLight text-xs">pairing yours!</p>
              <p className="font-GothicMedium text-xl">상품 구매</p>
            </div>
            <div className="space-y-1">
              <p className="text-end text-xs">
                <p>PAIRING에 등록된 상품을</p>
                <p>구경하고 구매해보세요!</p>
              </p>
              <p className="text-md text-end font-Gothic">상품 사러 가기 →</p>
            </div>
          </div>
          <img
            src="/img/extra-product-img.png"
            alt="product_img_err"
            className="w-1/2 animate-doong-sil object-cover"
          />
        </div>
      </Link>
    </>
  );
}

export default SalePage;
