import { Badge } from "@/components/ui/badge";

function ProductTypeC() {
  return (
    <>
      <div className="mx-8 box-border flex justify-between border-2 border-solid border-black1 p-4">
        <img
          src="/img/extra-product-img.png"
          alt="product_err"
          className="size-20 object-cover"
        />
        <div className="flex h-20 w-44 flex-col justify-center px-2 font-GothicLight">
          <div className="flex text-xxs">
            <Badge>왼쪽</Badge>
            <Badge>라이브</Badge>
          </div>
          <div className="my-1 text-xs">버즈 라이브 왼쪽(조합ㄴㄴ)</div>
          <div className="text-left text-xs">₩ 30,000</div>
        </div>
      </div>
    </>
  );
}

export default ProductTypeC;
