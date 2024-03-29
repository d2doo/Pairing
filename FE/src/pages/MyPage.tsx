import InfoUser from "@/components/InfoUser";
import InfoSale from "@/components/InfoSale";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
function MyPage() {
  /*
    둘이 url은 같다. tab을 통해서 
    tab 디자인

  */
  return (
    <>
      <Tabs defaultValue="saleInfo" className="m-0 w-full p-0 font-Gothic">
        <TabsList className="m-0 grid w-full grid-cols-2  border-b border-b-black1 p-0 text-black1">
          <TabsTrigger
            value="saleInfo"
            className="h-full
          data-[state=active]:bg-black data-[state=active]:text-white1 "
          >
            기본 페이지
          </TabsTrigger>
          <TabsTrigger
            value="refactor"
            className="h-full
            data-[state=active]:bg-black data-[state=active]:text-white1"
          >
            정보 수정
          </TabsTrigger>
        </TabsList>
        <TabsContent value="saleInfo">
          <Tabs defaultValue="전체" className="w-full">
            <InfoSale />
          </Tabs>
        </TabsContent>
        <TabsContent value="refactor">
          <Tabs defaultValue="전체" className="w-full">
            <InfoUser />
          </Tabs>
        </TabsContent>
      </Tabs>
    </>
  );
}

export default MyPage;
