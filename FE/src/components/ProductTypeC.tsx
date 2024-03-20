function ProductTypeC() {
  return (
    <>
      <div className="flex justify-between mx-8 p-4 box-border border-solid border-black1 border-2">
        <img
          src="img/extra-product-img.png"
          alt="product_err"
          className="object-cover size-20"
        />
        <div className="flex flex-col justify-center w-44 h-20 px-2 font-GothicLight">
          <div className="flex text-[0.563rem]">
            <div className="w-[3.375rem] border border-blue1 rounded-xl text-center">
              왼쪽
            </div>
            <div className="w-[3.375rem] border border-blue1 rounded-xl text-center">
              라이브
            </div>
          </div>
          <div className="text-xs my-1">버즈 라이브 왼쪽(조합ㄴㄴ)</div>
          <div className="text-xs">₩ 30,000</div>
        </div>
      </div>
    </>
  );
}

export default ProductTypeC;
