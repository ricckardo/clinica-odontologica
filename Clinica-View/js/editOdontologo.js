function editOdontologo(id, nombre, apellido, numeroMatricula) {
    Swal.fire({
      title: 'Modificar Odontólogo',
      html: `
        <input id="nombre" class="swal2-input" value="${nombre}">
        <input id="apellido" class="swal2-input" value="${apellido}">
        <input id="numeroMatricula" class="swal2-input" value="${numeroMatricula}">
      `,
      confirmButtonText: 'Guardar',
      focusConfirm: false,
      preConfirm: () => {
        const nombre = document.getElementById('nombre').value;
        const apellido = document.getElementById('apellido').value;
        const numeroMatricula = document.getElementById('numeroMatricula').value;
        return { nombre, apellido, numeroMatricula };
      }
    }).then((result) => {
      if (result.isConfirmed) {
        const odontologo = result.value;
        // Aquí puedes agregar el código para actualizar el odontólogo en el servidor
      }
    });
  }