import type { ICategorias } from "../../../types/Categorias";
import type { Product } from "../../../types/Product";
import { logout } from "../../../utils/auth";

//Iniciamos un carrito si no existe
if (!localStorage.getItem("carrito")) {
    localStorage.setItem("carrito", JSON.stringify([]))
}

//Traemos los productos de la base de datos
const resCategorias = await fetch("/data/categorias.json");
const categorias: ICategorias[] = await resCategorias.json();

const resProductos = await fetch("/data/productos.json");
const productos: Product[] = await resProductos.json();


//Verificamos que exista en localstorage el "carrito"

// 🔹 Seleccionamos la lista de categorías en la barra lateral


function mostrarCategorias() {
    const categoriaList = document.querySelector<HTMLUListElement>("#lista-categorias");

    categorias.forEach(categoria => {

        const li = document.createElement("li");
        li.textContent = categoria.nombre;
        categoriaList?.appendChild(li);

        li.addEventListener("click", (event: MouseEvent) => {
            event.preventDefault();

            // 🔹 Quitar activo de todas
            const todas = document.querySelectorAll("#lista-categorias li");
            todas.forEach(c => c.classList.remove("activo"));

            // 🔹 Marcar solo la clickeada
            li.classList.add("activo");

            // 🔹 Filtrar productos
            const contenedor = document.querySelector<HTMLDivElement>("#productos-items");
            const categoriaFiltro = categoria.nombre;

            const filtrados = productos.filter((p: Product) =>
                p.categorias.some((catId: any) => {
                    const cat = categorias.find(c => c.id === catId);
                    return cat?.nombre === categoriaFiltro;
                })
            );

            if (contenedor) {
                contenedor.innerHTML = "";
                mostrarProductos(filtrados);
            }
        });
    });

    // 🔹 Caso especial: Todos los productos
    const allProductos = document.querySelector<HTMLLIElement>("#all-products");
    allProductos?.addEventListener("click", () => {
        // Quitar activo de todas
        const todas = document.querySelectorAll("#lista-categorias li");
        todas.forEach(c => c.classList.remove("activo"));

        // Marcar solo "Todos"
        allProductos.classList.add("activo");

        // Mostrar todos los productos
        mostrarProductos(productos);
    });
}

mostrarCategorias();


// 🔹 Contenedor principal de productos
const contenedorProductos = document.querySelector<HTMLDivElement>("#productos-items");

function mostrarProductos(productos: Product[]) {
    // Limpiamos antes de renderizar
    if (contenedorProductos) {
        contenedorProductos.innerHTML = "";
    }

    // Recorremos y mostramos cada producto
    productos.forEach(productos => {
        const articulo = document.createElement("article");
        articulo.classList.add("p-articulo");

        const imagen = document.createElement("img");
        imagen.classList.add("p-imagen");
        imagen.src = productos.imagen;

        const pDetalles = document.createElement("div")
        pDetalles.classList.add("p-caracteristica");

        //Elementos de detalle
        const categoria = document.createElement("p");
        categoria.classList.add("p-categoria");
        categoria.textContent = productos.categorias
            .map((catId: any) => {
                const cat = categorias.find(c => c.id === catId);
                return cat?.nombre;
            })
            .join(", ");

        const titulo = document.createElement("h3");
        titulo.classList.add("p-titulo")
        titulo.textContent = productos.nombre;

        const descripcion = document.createElement("p");
        descripcion.classList.add("p-descripcion")
        descripcion.textContent = productos.descripcion;

        const precio = document.createElement("p");
        precio.textContent = `Precio: $ ${productos.precio}`;
        precio.classList.add("p-precio");

        const disponible = document.createElement("p");
        disponible.classList.add("p-disponible");

        if (productos.disponible == true) {
            disponible.textContent = "Disponible";
        } else {
            disponible.textContent = "No disponible";
            disponible.style.backgroundColor = "red";
        }

        pDetalles.appendChild(categoria);
        pDetalles.appendChild(titulo);
        pDetalles.appendChild(descripcion);
        pDetalles.appendChild(precio);
        pDetalles.appendChild(disponible);

        articulo.appendChild(imagen);
        articulo.appendChild(pDetalles);

        contenedorProductos?.appendChild(articulo);

        //Evento a cada imagen para mostrar el producto
        imagen.addEventListener("click", () => {

                mostrarDetalle(productos);
            
        })
    })
}

// Mostramos todos los productos al inicio
mostrarProductos(productos);



// 🔹 Evento de búsqueda en vivo
const buscador = document.querySelector<HTMLInputElement>("#buscador");
buscador?.addEventListener("input", () => {
    const texto = buscador.value.toLowerCase();

    // Filtramos productos por coincidencia en nombre
    const filtrados: Product[] = productos.filter((p: Product) => p.nombre.toLowerCase().includes(texto));

    if (filtrados.length === 0 && contenedorProductos) {
        contenedorProductos.innerHTML = "";
        const cartel = document.createElement("h2");
        cartel.textContent = "No hay coincidencias en su búsqueda";
        cartel.classList.add("mensaje");
        contenedorProductos.appendChild(cartel);
    } else {
        mostrarProductos(filtrados);
    }
})

// 🔹 Función para mostrar alerta temporal al agregar producto


// 🔹 Actualizamos el valor del carrito en la barra superior
const valorCarrito = document.querySelector<HTMLSpanElement>("#valor-carrito");

function mostrarValorCarrito() {
    const carrito = localStorage.getItem("carrito");
    const carritoProdu: Product[] = carrito ? JSON.parse(carrito) : [];

    if (valorCarrito) {
        valorCarrito.textContent = carritoProdu.length.toString();
    }
}

mostrarValorCarrito();






//Boton para salir de la sesion
const btnSesion = document.querySelector<HTMLElement>("#btn-sesion");
if (btnSesion) {
    btnSesion.addEventListener("click", () => {
        logout();
    })
}

function mostrarDetalle(producto: Product) {

    const main = document.querySelector('#main');

    if(main){
        main.innerHTML='';
    }

    const titulo = document.createElement('h1');
    titulo.textContent = producto.nombre;

    const precio = document.createElement('p');
    precio.textContent = `Precio: $ ${producto.precio}`;

    const disponible = document.createElement('p');
    disponible.classList.add('p-disponible')

    if (producto.disponible) {
        disponible.textContent = 'Disponible'
    } else {
        disponible.textContent = 'No disponible'
        disponible.style.backgroundColor = 'red';
    }

    const descripcion = document.createElement('p');
    descripcion.textContent = producto.descripcion;



    const imgP = document.createElement("img");
    imgP.src = producto.imagen;
    //Box Principal
    const box = document.createElement("div");
    box.classList.add("caja-p")

    //Box Contenedoras
    const c1 = document.createElement("div");
    c1.classList.add("img-c")

    const c2 = document.createElement("div");
    c2.classList.add("det-c");

    //Box de botones
    const btnBox = document.createElement("div");

    const btnAdd = document.createElement("button");
    btnAdd.classList.add("btnAgregar")
    btnAdd.textContent = "+";

    const cantidadSpan = document.createElement("span");
    cantidadSpan.textContent = "0";
    cantidadSpan.classList.add("cant-span")

    const btnSubstract = document.createElement("button");
    btnAdd.classList.add("btnProducto");
    btnSubstract.textContent = "-";

    btnBox.appendChild(btnAdd);
    btnAdd.appendChild(cantidadSpan);
    btnBox.appendChild(btnSubstract);

    //Botones para agregar al carrito
    const btnProducto = document.createElement("div");
    btnProducto.classList.add("btnProducto")

    const agregarCarrito = document.createElement("button");
    agregarCarrito.classList.add("agregar-carrito");

    const volver = document.createElement("button");
    volver.classList.add("volver");

    btnProducto.appendChild(agregarCarrito);
    btnAdd.appendChild(volver);


    c1.appendChild(imgP);

    c2.appendChild(titulo);
    c2.appendChild(precio);
    c2.appendChild(disponible);
    c2.appendChild(descripcion);
    c2.appendChild(btnBox);

    box.appendChild(c1);
    box.appendChild(c2);

    if (main) {
        main.appendChild(box)
    }

}