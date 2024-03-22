import {useLocalAxios} from "@/utils/axios.ts";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useAuthStore} from "@/stores/auth.ts";

export const KakaoAuthCallback = () => {
    const navigate = useNavigate();
    const authStore = useAuthStore();
    const localAxios = useLocalAxios();

    useEffect(() => {
        const code = new URL(document.location.toString()).searchParams.get('code');

        if (authStore.accessToken) {
            navigate('/');
            return;
        }

        localAxios.post('/member/login/kakao', null, {
            params: { code }
        })
            .then((response) => {
                authStore.setAuth(response.data);
                navigate('/');
            })
    }, []);

    return (
        <></>
    );
};