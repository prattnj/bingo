import React, {useEffect} from 'react';
import './Home.css';
import {getToken} from "../util";

function Home() {
    useEffect(() => {
        if (!getToken()) {
            window.location.href = '/login'
        }
    }, [])

    return (
        <div>
            home
        </div>
    );
}

export default Home;
