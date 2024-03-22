function ProductTypeC() {
  return (
    <>
      <div className="mx-8 box-border flex justify-between border-2 border-solid border-black1 p-4">
        <img
          src="img/extra-product-img.png"
          alt="product_err"
          className="size-20 object-cover"
        />
        <div className="flex h-20 w-44 flex-col justify-center px-2 font-GothicLight">
          <div className="flex text-[0.563rem]">
            <div className="w-[3.375rem] rounded-xl border border-blue1 text-center">
              왼쪽
            </div>
            <div className="w-[3.375rem] rounded-xl border border-blue1 text-center">
              라이브
            </div>
          </div>
          <div className="my-1 text-xs">버즈 라이브 왼쪽(조합ㄴㄴ)</div>
          <div className="text-xs">₩ 30,000</div>
        </div>
      </div>
    </>
  );
}

export default ProductTypeC;
