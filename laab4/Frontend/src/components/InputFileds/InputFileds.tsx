import React, {FormEvent, useContext, useEffect} from "react";
import SelectInput from "../SelectInput";
import {DotsFormContext} from "./InputFieldContext";
import toast from "react-hot-toast";
import {getLogin} from "../../redux/login";
import {AuthorizationStore} from "../../redux/authorizationStore";
import {getPassword} from "../../redux/password";
import Dots from "../../views/Dots";
import {useNavigate} from "react-router-dom";
import {isNumber} from "util";


function InputFields() {
    const context = useContext(DotsFormContext)

    useEffect(() => {
        fetch("/api/dots", {
            method: "GET",
            headers: {
                "Authorization": "Basic " + btoa(getLogin(AuthorizationStore.getState()) + ":"
                    + getPassword(AuthorizationStore.getState()))
            }
        })
            .then(r => r.json())
            .then(r => context?.setDots(r))
    }, [])

    let validateX = (x: number) => x <= 3 && x >= -3;
    let validateY = (y: string) => parseFloat(y) <= 3 && parseFloat(y) >= -3;
    let validateR = (r: number) => r <= 3 && r >= 0;


    function parseFormSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (!context || context.getX == null || context.getY == null || !context.getR == null){
            toast.error("Fill all boxes with coordinates!")
            return
        }
        if(!validateX(context.getX) || !validateY(context.getY as string) || !validateR(context.getR)){
            toast.error("Coordinates are not valid!")
            return
        }
        let formData = new FormData();
        formData.append("x", context.getX.toString())
        formData.append("y", context.getY.toString())
        formData.append("r", context.getR.toString())
        fetch("/api/dots", {
            method: "POST",
            headers: {"Authorization": "Basic " + btoa(getLogin(AuthorizationStore.getState()) + ":" + getPassword(AuthorizationStore.getState()))},
            body: formData
        })
            .then(r => {
                if (r.ok) return r
                else throw new Error(r.statusText)
            })
            .then(r => r.json())
            .then(r => {
                context.addDot(r)
            })
            .catch(e => toast.error(e.message));
    }

    function sendClear(){
        fetch("/api/dots", {
            method: "DELETE",
            headers: {"Authorization": "Basic " + btoa(getLogin(AuthorizationStore.getState()) + ":" + getPassword(AuthorizationStore.getState()))},
        })
            .then(r => {
                if (r.ok) {
                    context?.setDots([])
                }
            })
    }

    if (!context) return (<></>);
    return (
        <form onSubmit={e=> parseFormSubmit(e)}>
            <div className="flex flex-col w-full md:w-80 p-5 bg-gray-300 md:h-full justify-start shadow-xl hover:shadow-2xl">
                <SelectInput name={"X"}
                             value={context.getX}
                             value_setter={context.setX}
                             label={"Select X: "}
                             labels={[-3, -2, -1, 0, 1, 2, 3]}/>
                <label className="font-semibold my-4">Select R: <input
                    type={"text"}
                    value={context.getY}
                    className="bg-gray-700 border border-gray-600 text-sm rounded-md mt-2
                    border-s-2 block w-full p-2.5 placeholder-gray-400 text-white"
                    onChange={e => context.setY(e.target.value)}
                /></label>
                <SelectInput name={"R"}
                             value={context.getR}
                             value_setter={context.setR}
                             label={"Select R:"}
                             labels={[-3, -2, -1, 0, 1, 2, 3]}/>
                <button type={"submit"}
                        className="w-full bg-gray-900 mt-6 text-white p-2 rounded-md hover:bg-gray-800 focus:outline-none
                        focus:bg-black focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 transition-colors duration-300"
                >SUBMIT</button>
                <button type={"button"} onClick={sendClear}
                        className="w-full bg-gray-900 mt-4 text-white p-2 rounded-md hover:bg-gray-800 focus:outline-none
                        focus:bg-black focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 transition-colors duration-300"
                >CLEAR</button>
            </div>
        </form>
    )
}

export default InputFields;