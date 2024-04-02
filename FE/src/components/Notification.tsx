import { useEffect, useState } from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { NotificationResponse } from "@/types/Notification";
import NotificationDetail from "./NotificationDetail";
interface NotificationProps {
  openhandler: (notificationId: bigint) => void;
  closehandler: (notificationId: bigint) => void;
  notification: NotificationResponse;
}
function Notification(props: NotificationProps) {

  return (
    <>
      <div className="flex flex-wrap w-full">
        <AlertDialog>
          <AlertDialogTrigger asChild>
            <Button variant="outline" className="space-y-1 text-sm my-1">자세히 보기</Button>
          </AlertDialogTrigger>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>
                {props.notification?.notificationType == 'confirm' ? '합의 제안' : '일반 알림'}
              </AlertDialogTitle>
              <AlertDialogDescription>
                <div className="py-1">
                  {/* <NotificationDetail key={props.notification?.notificationId} notification={props.notification} /> */}
                  <Card className="font-[Gothic] bg-white border border-gray-200 shadow-md rounded-md">
                    <CardHeader className="bg-blue1 text-white rounded-t-md">
                      <CardTitle className="flex items-center">
                        {/* 포인트 색상 설정 */}
                        <div className="bg-yellow-500 rounded-full w-6 h-6 flex items-center justify-center mr-2">
                          <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" viewBox="0 0 20 20" fill="currentColor">
                            <path fillRule="evenodd" d="M10 2a1 1 0 011 1v7h4a1 1 0 010 2h-5v1a1 1 0 01-2 0V12H5a1 1 0 010-2h4V3a1 1 0 011-1z" clipRule="evenodd" />
                            <path fillRule="evenodd" d="M3 8a1 1 0 011-1h8V5a1 1 0 012 0v2h3a1 1 0 110 2h-3v2a1 1 0 01-2 0V9H4a1 1 0 01-1-1z" clipRule="evenodd" />
                          </svg>
                        </div>
                        {/* 내용 */}
                        <div>
                          {props.notification?.content}
                        </div>
                      </CardTitle>
                    </CardHeader>
                    <CardContent>
                      <div className="flex flex-row justify-around">
                        {props.notification.units && props.notification.units.map((unit, index) => (
                          <div key={index} className="border border-gray-200 rounded-md mt-6">
                            <img
                              src={unit.unitImages[0] ? unit.unitImages[0] : '../assets/images/case.png'}
                              alt="Unit Image"
                              className="w-full h-auto object-cover rounded-t-md"
                            />
                            <div className="p-4">
                              <div>주인: {unit.nickname}</div>
                              <div>연식: {unit.age}</div>
                              <div>신뢰도: {unit.score}</div>
                            </div>
                          </div>
                        ))}
                      </div>
                    </CardContent>
                  </Card>
                </div>


              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter className="flex flex-row justify-end">
              <AlertDialogCancel className="w-12">
                <Button onClick={() => props.closehandler(props.notification.notificationId)} className="bg-white h-full">{props.notification?.notificationType == 'confirm' ? 'X' : '닫기'}</Button>
              </AlertDialogCancel>
              <AlertDialogAction className="w-12">
                <Button onClick={() => props.openhandler(props.notification.notificationId)}>{props.notification?.notificationType == 'confirm' ? 'O' : '읽기'}</Button>
              </AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </div>
    </>
  );
}

export default Notification;
