import React from "react"
import {Link} from "react-router-dom";
import {useAppDispatch} from "../../redux/hooks";
import {setLogin} from "../../redux/login";
import {setPassword} from "../../redux/password";

function Header(){
    return (
        <div>
            <h1 className="text-3xl font-semibold mb-6 text-red text-center">Лабораторная работа 4 Var 10</h1>
            <h1 className="text-xs font-semibold mb-6 text-center">Гафурова Фарангиз Фуркатовна P3220</h1>
        </div>
    )
}

export default Header;
