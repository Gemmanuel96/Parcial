import { categorias } from "../../../data/data";
import { PRODUCTS } from "../../../data/data";
import type { Product } from "../../../types/Product";


//Mostramos las categorias en la barra lateral
const categoriaList = document.querySelector<HTMLUListElement>("#categoria-list");
console.log(categoriaList);

function mostrarCategorias() {
    categorias.forEach(categoria => {


        const li = document.createElement("li") as HTMLElement;
        li.textContent = categoria.nombre;

        categoriaList?.appendChild(li);

        li?.addEventListener("click", (Event: MouseEvent) => {
            Event.preventDefault();

            const contenedor = document.querySelector<HTMLDivElement>("#productos");
            const categoriaFiltro = categoria.nombre;

            //Combinacion de metodos de busqueda, filter + some para poder obtener el dato del array
            const filtrados = PRODUCTS.filter(p => p.categorias.some(c => c.nombre === categoriaFiltro));

            console.log(filtrados);

            if (contenedor) {
                contenedor.innerHTML = "";
                mostrarProductos(filtrados);
            }

        })
    })
}
mostrarCategorias();

//Evento de todos los productos
const allProductos = document.querySelector<HTMLLIElement>("#all-productos");

allProductos?.addEventListener("click", () => {
    mostrarProductos(PRODUCTS);
});


//Mostramos los pedidos en la pagina de compra

const contenedorProductos = document.querySelector<HTMLDivElement>("#productos");

function mostrarProductos(productos: Product[]) {

    if (contenedorProductos) {
        contenedorProductos.innerHTML = "";
    }

    productos.forEach(productos => {

        const articulo = document.createElement("article");

        const imagen = document.createElement("img");
        imagen.src = productos.imagen;

        const nombre = document.createElement("h3");
        nombre.textContent = productos.nombre;

        const descripcion = document.createElement("p");
        descripcion.textContent = productos.descripcion;

        const precio = document.createElement("p");
        precio.textContent = `Precio: $ ${productos.precio}`
        precio.classList.add("precio");

        const btnAgregar = document.createElement("button") as HTMLButtonElement;
        btnAgregar.textContent = "Agregar a carrito";
        btnAgregar.classList.add("agregar")

        articulo.appendChild(imagen);
        articulo.appendChild(nombre);
        articulo.appendChild(descripcion);
        articulo.appendChild(precio);
        articulo.appendChild(btnAgregar);

        btnAgregar.addEventListener("click", () => {
            const carrito = localStorage.getItem("carrito");
            const carritoProductos: Product[] = carrito ? JSON.parse(carrito) : [];
            carritoProductos.push(productos);
            localStorage.setItem("carrito", JSON.stringify(carritoProductos));
            console.log("Producto agregado al carrito");
            mostrarAlerta();
            mostrarValorCarrito();
        })

        contenedorProductos?.appendChild(articulo);


    })
}

mostrarProductos(PRODUCTS);

//Evento de busqueda in live

const buscador = document.querySelector<HTMLInputElement>("#buscador");

buscador?.addEventListener("input", () => {
    const texto = buscador.value;

    const filtrados = PRODUCTS.filter(p => p.nombre.toLowerCase().includes(texto));

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


function mostrarAlerta() {
    const alerta = document.querySelector<HTMLDivElement>("#alerta");

    if (alerta) {

        alerta.style.display = "block";

        setTimeout(() => {
            alerta.style.display = "none";
        }, 2000)
    }

}

const valorCarrito = document.querySelector<HTMLElement>("#valor-carrito");

function mostrarValorCarrito() {

    const carrito = localStorage.getItem("carrito");
    const carritoProdu: Product[] = carrito ? JSON.parse(carrito) : [];


    if (valorCarrito) {
        valorCarrito.textContent = carritoProdu.length.toString();
    }

}

mostrarValorCarrito();

