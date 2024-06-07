import React, {useEffect} from 'react';
import './Home.css';
import {getToken} from "../util";
import {Tabs} from "../components/tab/Tabs";
import {Tab} from "../components/tab/Tab";

function Home() {
    useEffect(() => {
        if (!getToken()) window.location.href = '/login'
    }, [])

    return (
        <div>
            <Tabs>
                <Tab title={'My Boards'}>
                    <div>My Boards</div>
                </Tab>
                <Tab title={'Public Boards'}>
                    <div>Public Boards</div>
                </Tab>
            </Tabs>
        </div>
    );
}

export default Home;
