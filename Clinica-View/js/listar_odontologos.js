const tableBody = document.querySelector("#odontologosTable tbody");
const apiURL = "http://localhost:8080";
function fetchOdontologos() {
  // listando los odontologos

  fetch(`${apiURL}/odontologos`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
                <td>${odontologo.id}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.numeroMatricula}</td>
                <td>
                  <button class="btn btn-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.nombre}', '${odontologo.apellido}', '${odontologo.numeroMatricula}')">Modificar</button>
                  <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
                </td>
              `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      let mensaje = `Error fetching data: ${error}`;
      // alert(mensaje);
      Swal.fire({
        title: '¡Error!',
        text: mensaje,
        icon: 'error',
        confirmButtonText: 'Aceptar'
      });
    });

  // modificar un odontologo


  // eliminar un odontologo
}

fetchOdontologos();
