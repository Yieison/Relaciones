$(document).ready(function() {
    function listarCategorias() {
        $.ajax({
            url: '/categorias',
            type: 'GET',
            success: function(categorias) {
                $('#listaCategorias').empty();
                categorias.forEach(categoria => {
                    $('#listaCategorias').append(`<li>${categoria.nombre} <button onclick="editarCategoria(${categoria.id}, '${categoria.nombre}')">Editar</button> <button onclick="eliminarCategoria(${categoria.id})">Eliminar</button></li>`);
                });
            }
        });
    }

    $('#categoriaForm').on('submit', function(e) {
        e.preventDefault();
        var id = $('#categoriaId').val();
        var nombre = $('#nombreCategoria').val();

        var url = '/categorias';
        var type = 'POST';
        var data = JSON.stringify({ nombre: nombre });

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
                listarCategorias();
                $('#categoriaForm').trigger('reset');
                $('#categoriaId').val('');
            },
            error: function(error) {
                alert('Error al guardar la categoría');
                console.error(error);
            }
        });
    });

    window.editarCategoria = function(id, nombre) {
        $('#categoriaId').val(id);
        $('#nombreCategoria').val(nombre);
    }

    window.eliminarCategoria = function(id) {
        $.ajax({
            url: `/categorias/${id}`,
            type: 'DELETE',
            success: function() {
                listarCategorias();
            },
            error: function(error) {
                alert('Error al eliminar la categoría');
                console.error(error);
            }
        });
    }

    listarCategorias();
});
