
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('pointForm');
    const resultTd = document.getElementById('result');
    const timeTd = document.getElementById('time');
    const executionTimeTd = document.getElementById('executionTime');
    const historyTable = document.getElementById('historyTable');
    const point = document.getElementById('point');
    const submitBtn = document.getElementById('submit');
    let r = null;


    function getR(){
        return r;
    }
    // Функция для отображения истории из Local Storage

    function setOnClick(element) {
        element.onclick = function () {
            r = this.value;

            buttons.forEach(function (element) {
                element.style.boxShadow = "";
                element.style.transform = "";
            });
            this.style.boxShadow = "0 0 40px 5px #f41c52";
            this.style.transform = "scale(1.05)";
            drawFig(r);
            call();
        }
    }

    let buttons = document.querySelectorAll("input[name=R-button]");
    buttons.forEach(setOnClick);

    submitBtn.addEventListener('click', function (e) {

        e.preventDefault();
        const xr = document.querySelectorAll('input[type=radio]:checked');
        const yy = document.getElementById('Y-input').value;
        const yyy = yy.replace(',', '.');
        const y = parseFloat(yyy);
        let x = null;

        if (xr.length > 0) {
            x = parseFloat(xr[0].value); // Берем значение из выбранной радиокнопки
        } else {
            alert('Пожалуйста, выберите значение X.');
            return;
        }
        if (isNaN(y)){
            alert('Пожалуйста, выведите значение Y.');
            return;
        }

        if (isNaN(x) || isNaN(y) || isNaN(r) || r <= 0) {
            alert('Пожалуйста, введите корректные значения.');
            return;
        }
        // Проверка значения Y
        if (y < -3 || y > 3) {
            alert('Значение Y должно быть в пределах от -3 до 3 включительно.');
            return;
        }

        send_intersection_rq(x, y, r);

        /*fetch(`script.php?x=${x}&y=${y}&r=${r}`)
            .then(response => response.json().then(data => {

                resultTd.textContent = data.result ? 'Попадание' : 'Промах';
                timeTd.textContent = data.time;
                executionTimeTd.textContent = data.execution_time.toFixed(Int8Array) + ' микросек';
                point.setAttribute("cx", 150 + ((120/r) * x));
                point.setAttribute("cy", 150 - ((120/r) * y));
                if(!data.result){
                    point.setAttribute("fill", "red");
                }else{
                    point.setAttribute("fill", "green");
                }

                // Сохранить результат в Local Storage
                saveResultToLocalStorage(data);
                // Отобразить обновленную историю
                displayHistoryFromLocalStorage();
            }))
            .catch(error => console.error(error));

         */
    });

    function enable_graph() {
        if (graph_click_enabled) {
            elt.removeEventListener('click', handleGraphClick);
            graph_click_enabled = false;
        } else {
            elt.addEventListener('click', handleGraphClick);
            graph_click_enabled = true;
        }
    }

    enable_graph();

    function handleGraphClick (evt) {
        let r_val = getR();

        console.log(r_val);

        if (r_val == null || r_val === "") {
            alert("Choose R!");
            return;
        }

        const rect = elt.getBoundingClientRect();
        const x = evt.clientX - rect.left;
        const y = evt.clientY - rect.top;

        // Note, pixelsToMath expects x and y to be referenced to the top left of
        // the calculator's parent container.
        const mathCoordinates = calculator.pixelsToMath({x: x, y: y});

        // if (!inRectangle(mathCoordinates, calculator.graphpaperBounds.mathCoordinates)) return;

        console.log('setting expression...');
        console.log(mathCoordinates);

        send_intersection_rq(mathCoordinates.x - 0.424, mathCoordinates.y + 0.202, r);
    }

});

function send_intersection_rq(x,y,r) {
    drawPoint(x, y, r);
    const resultsBody = document.getElementById("resultsbody");
    const url = window.location.href + "controller"

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `x=${x}&y=${y}&r=${r}`
    })
        .then((response) => response.text())
        .then((data) => {
            console.log(data);
            window.location.href = 'table.jsp';
            /*resultsBody.innerHTML = data;*/
        })
        .catch((error) => alert(error));
}

function call() {

    const xr = document.querySelectorAll('input[type=radio]:checked');
    const yy = document.getElementById('Y-input').value;
    const yyy = yy.replace(',', '.');
    const y = parseFloat(yyy);
    let x = null;

    if (xr.length > 0) {
        x = parseFloat(xr[0].value); // Берем значение из выбранной радиокнопки
    }

    if (isNaN(x) || isNaN(y)) {
    } else {
        drawPointXY(x, y);
    }
}