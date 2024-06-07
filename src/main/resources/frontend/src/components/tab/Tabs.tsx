import {ReactElement, useState} from "react";
import {TabProps} from "./Tab";
import './tab.css'

interface TabsProps {
    children: ReactElement[],
}

export const Tabs = (props: TabsProps) => {
    const [selected, setSelected] = useState<TabProps>(props.children[0].props);

    const getTabClass = (title: string) => {
        let width;
        if (props.children.length < 1 || props.children.length > 5) width = '';
        else width = 'tw-' + props.children.length;
        return width + ' tabs-tab ' + (selected?.title === title ? 'tabs-selected' : 'tabs-unselected');
    }

    return (
        <div className={'tabs-all'}>
            <div className={'tabs-tabs'}>
                {props.children.map((tab) => {
                    let props = tab.props as TabProps
                    let title = props.title
                    return (
                        <div
                            onClick={() => {
                                if (selected?.title !== title) setSelected(props);
                            }}
                            key={title}
                            className={getTabClass(title)}
                        >
                            {title}
                        </div>
                    )})
                }
            </div>
            {selected?.children}
        </div>
    );
}