document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('pointForm');
  const resultTd = document.getElementById('result');
  const timeTd = document.getElementById('time');
  const executionTimeTd = document.getElementById('executionTime');
  const historyTable = document.getElementById('historyTable');
  const clearHistoryButton = document.getElementById('clearHistoryButton');
  const point = document.getElementById('point');

  // Обработчик нажатия на кнопку "Стереть историю"
  clearHistoryButton.addEventListener('click', function () {
    // Очистить Local Storage
    localStorage.removeItem('history');

    // Очистить таблицу истории (кроме шапки)
    while (historyTable.rows.length > 1) {
        historyTable.deleteRow(1);
    }
  });

  // Функция для сохранения результатов в Local Storage
  function saveResultToLocalStorage(result) {
      const history = JSON.parse(localStorage.getItem('history')) || [];
      history.push(result);
      localStorage.setItem('history', JSON.stringify(history));
  }

  // Функция для отображения истории из Local Storage
  function displayHistoryFromLocalStorage() {
      const history = JSON.parse(localStorage.getItem('history')) || [];

      // Очистить таблицу, оставив первую строку (шапку)
      while (historyTable.rows.length > 1) {
          historyTable.deleteRow(1);
      }

      history.forEach((entry, index) => {
          const row = historyTable.insertRow();
          row.insertCell(0).textContent = index + 1;
          row.insertCell(1).textContent = entry.x;
          row.insertCell(2).textContent = entry.y;
          row.insertCell(3).textContent = entry.r;
          row.insertCell(4).textContent = entry.result ? 'Попадание' : 'Промах';
          row.insertCell(5).textContent = entry.time;
          row.insertCell(6).textContent = entry.execution_time.toFixed(Int8Array) + ' микросек';
      });
  }

  function setOnClick(element) {
    element.onclick = function () {
      r = this.value;
      buttons.forEach(function (element) {
        element.style.boxShadow = "";
        element.style.transform = "";
      });
      this.style.boxShadow = "0 0 40px 5px #f41c52";
      this.style.transform = "scale(1.05)";
    }
  }

  let buttons = document.querySelectorAll("input[name=R-button]");
  buttons.forEach(setOnClick);


  // Отобразить историю при загрузке страницы
  displayHistoryFromLocalStorage();

  form.addEventListener('submit', function (e) {

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

      fetch(`script.php?x=${x}&y=${y}&r=${r}`)
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
  });
});