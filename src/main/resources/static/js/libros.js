$(document).ready(function() {
    function listarAutores() {
        $.ajax({
            url: '/autores',
            type: 'GET',
            success: function(autores) {
                $('#autorLibro').empty();
                autores.forEach(autor => {
                    $('#autorLibro').append(new Option(autor.nombre, autor.id));
                });
            }
        });
    }

    function listarLibros() {
        $.ajax({
            url: '/libros',
            type: 'GET',
            success: function(libros) {
                $('#listaLibros').empty();
                libros.forEach(libro => {
                    $('#listaLibros').append(`<li>${libro.titulo} (Autor: ${libro.autorId}) <button onclick="editarLibro(${libro.id}, '${libro.titulo}', ${libro.autorId})">Editar</button> <button onclick="eliminarLibro(${libro.id})">Eliminar</button></li>`);
                });
            }
        });
    }

    $('#libroForm').on('submit', function(e) {
        e.preventDefault();
        var id = $('#libroId').val();
        var titulo = $('#tituloLibro').val();
        var autorId = $('#autorLibro').val();

        var url = '/libros';
        var type = 'POST';
        var data = JSON.stringify({ titulo: titulo, autorId: autorId });

        if (id) {
            url += `/${id}`;
            type = 'PUT';
        }

        $.ajax({
            url: url,
            type: type,
            contentType: 'application/json',
            data: data,
            success: function() {
                listarLibros();
                $('#libroForm').trigger('reset');
            },
            error: function(error) {
                alert('Error al guardar el libro');
                console.error(error);
            }
        });
    });

    window.editarLibro = function(id, titulo, autorId) {
        $('#libroId').val(id);
        $('#tituloLibro').val(titulo);
        $('#autorLibro').val(autorId);
    }

    window.eliminarLibro = function(id) {
        $.ajax({
            url: `/libros/${id}`,
            type: 'DELETE',
            success: function() {
                listarLibros();
            },
            error: function(error) {
                alert('Error al eliminar el libro');
                console.error(error);
            }
        });
    }

    listarAutores();
    listarLibros();
});
