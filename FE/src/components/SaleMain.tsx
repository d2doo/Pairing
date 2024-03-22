import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

function SalePage() {
  return (
    <>
      {/* 개별등록 */}
      <div className="flex h-[calc((100vh-3.5rem)/2)] justify-evenly bg-white1 p-10">
        <img
          src="/img/extra-product-img.png"
          alt="product_img_err"
          className="w-1/2 object-cover"
        />
        <div className="mt-auto flex h-full flex-col justify-end py-5 font-GothicLight">
          <p className="text-lg">개별등록</p>
          <p className="text-xs">내 상품을 등록해보세요.</p>
          <p className="text-md font-GothicMedium">개별 등록하러 가기 →</p>
        </div>
      </div>

      {/* 조립등록 */}
      <div className="h-[calc((100vh-3.5rem)/2)] bg-blue1 p-10">
        <Dialog>
          <DialogTrigger asChild>
            <div className="flex h-full justify-evenly space-x-1">
              <div className="flex h-full flex-col items-end justify-end py-5 font-GothicLight">
                <p className="text-lg">조립등록</p>
                <p className="text-end text-xs">
                  다른사람의 상품을 내 상품과 조립하고 등록해보세요.
                </p>
                <p className="text-md font-GothicMedium">조립하러 가기 →</p>
              </div>
              <img
                src="/img/extra-product-img.png"
                alt="product_img_err"
                className="w-1/2 object-cover"
              />
            </div>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>이전에 등록한 제품을 조립하려면?</DialogTitle>
              <DialogDescription>동료 찾아 조립하기</DialogDescription>
            </DialogHeader>
            <DialogHeader>
              <DialogTitle>새로 처음부터 조립하려면?</DialogTitle>
              <DialogDescription>내 상품부터 등록하기</DialogDescription>
            </DialogHeader>
          </DialogContent>
        </Dialog>
      </div>
    </>
  );
}

export default SalePage;
