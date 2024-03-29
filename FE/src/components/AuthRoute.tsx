import {useAuthStore} from "@/stores/auth.ts";
import {Navigate, Outlet} from "react-router-dom";

export const AuthRoute = () => {
    const authStore = useAuthStore();

    return (
        authStore.accessToken
            ? <Outlet />
            : <Navigate to="/login" />
    );
};