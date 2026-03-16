let tasks = [];

window.onload = function(){

    let savedTasks = localStorage.getItem("tasks");

    if(savedTasks){
        tasks = JSON.parse(savedTasks);
        displayTasks();
    }

    let mode = localStorage.getItem("mode");

    if(mode === "dark"){
        document.body.classList.add("dark-mode");
    }
}

function addTask(){

    let input = document.getElementById("taskInput");
    let text = input.value;

    if(text === ""){
        alert("Enter a task");
        return;
    }

    tasks.push({
        name: text,
        done: false
    });

    input.value = "";

    saveTasks();
    displayTasks();
}

function displayTasks(){

    let list = document.getElementById("taskList");
    list.innerHTML = "";

    tasks.forEach(function(task,index){

        let li = document.createElement("li");

        let span = document.createElement("span");
        span.innerText = "✔ " + task.name;

        if(task.done){
            span.classList.add("completed");
        }

        span.onclick = function(){
            tasks[index].done = !tasks[index].done;
            saveTasks();
            displayTasks();
        }

        let del = document.createElement("button");
        del.innerText = "❌";
        del.className = "deleteBtn";

        del.onclick = function(){
            tasks.splice(index,1);
            saveTasks();
            displayTasks();
        }

        li.appendChild(span);
        li.appendChild(del);

        list.appendChild(li);
    });
}

function saveTasks(){
    localStorage.setItem("tasks", JSON.stringify(tasks));
}

function clearAll(){
    tasks = [];
    saveTasks();
    displayTasks();
}

function toggleDarkMode(){

    document.body.classList.toggle("dark-mode");

    if(document.body.classList.contains("dark-mode")){
        localStorage.setItem("mode","dark");
    }else{
        localStorage.setItem("mode","light");
    }
}
