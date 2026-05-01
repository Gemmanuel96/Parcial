import type { Product } from "../../../types/Product";

const carrito = localStorage.getItem("carrito");
const productoCarrito: Product[] = carrito ? JSON.parse(carrito) : [];
//Contenedor de donde van los productos del carrito
const productosBox = document.querySelector<HTMLElement>("#productos-box");
console.log(productosBox);


// Agrupación de productos
// Usamos reduce para recorrer el array y acumular productos únicos con su cantidad
const agrupados: Product[] = Object.values(
    productoCarrito.reduce((acc: any, prod: Product) => {
        if (!acc[prod.id]) {
            // Si el producto no existe en el acumulador, lo agregamos con cantidad inicial 0
            acc[prod.id] = { ...prod, cantidad: 0 };
        } else {
            // Si ya existe, incrementamos la cantidad
            acc[prod.id].cantidad += 1;
        }
        return acc;
    }, {})
);

//Box Carrito

function mostrarCarrito() {

    //Mostrara el mensaje de que no hay productos en carrito por la pantalla

    if (productoCarrito.length === 0) {
        const mensaje = document.querySelector<HTMLElement>("#mensaje");
        const productoBox = document.querySelector<HTMLElement>("#productos-box")
        const total = document.querySelector<HTMLElement>("#total-box")
        if (mensaje && productoBox && total) {
            productoBox.style.display = "none";
            total.style.display = "none";
            mensaje.style.display = "block";
        }

    } else { //Mostrara los productos en pantalla cuando haya productos en el carrito

        agrupados.forEach(producto => {
            //Contenedores de producto + productos
            const productoItem = document.createElement("div");
            productoItem.classList.add("producto-item")

            const detalle = document.createElement("div");
            detalle.classList.add("detalle-item")

            const controles = document.createElement("div");
            controles.classList.add("controles-item");

            const imagen = document.createElement("img");
            imagen.src = producto.imagen

            productoItem.appendChild(imagen);

            const nombre = document.createElement("h3");
            nombre.textContent = producto.nombre;

            const descripcion = document.createElement("p");
            descripcion.textContent = producto.descripcion;


            detalle.appendChild(nombre);
            detalle.appendChild(descripcion);

            productoItem.appendChild(detalle);
            productoItem.appendChild(controles);

            productosBox?.appendChild(productoItem);

            //Controlador de Productos, el cual nos permitira agregar o aliminar productos del carrito
            // Botón -
            const btnMenos = document.createElement("button");
            btnMenos.textContent = "-";

            // Contador
            const cantidad = document.createElement("span");
            cantidad.textContent = contadorProductos(producto.id).toString();

            // Botón +
            const btnMas = document.createElement("button");
            btnMas.textContent = "+";

            controles.appendChild(btnMenos);
            controles.appendChild(cantidad);
            controles.appendChild(btnMas);


            btnMas.addEventListener("click", () => {
                agregarProducto(producto);
                cantidad.textContent = contadorProductos(producto.id).toString();
                console.log("Agregando 1 + de " + producto.nombre);
                mostrarTotal();
            });

            btnMenos.addEventListener("click", () => {
                eliminarProducto(producto);
                cantidad.textContent = contadorProductos(producto.id).toString();
                console.log("Eliminando 1 - de " + producto.nombre);
                mostrarTotal();
            });
        })
    }
}

mostrarCarrito();


//Funciones para los botones
function agregarProducto(prod: Product) {
    productoCarrito.push(prod);
    localStorage.setItem("carrito", JSON.stringify(productoCarrito));
}

function eliminarProducto(prod: Product) {
    const index = productoCarrito.findIndex(p => p.id === prod.id);
    if (index !== -1) {
        productoCarrito.splice(index, 1);
        localStorage.setItem("carrito", JSON.stringify(productoCarrito));
    }
}

//Nos devolvera el total de cada producto
function contadorProductos(id: number): number {
    return productoCarrito.filter(p => p.id === id).length;
}

function calcularTotal(): number {
    let total: number = 0;
    productoCarrito.forEach(p => {
        total += p.precio;
    })
    return total;
}


//Box del total
function mostrarTotal() {

    const subTotal = document.querySelector<HTMLElement>("#subtotal");
    const total = document.querySelector<HTMLElement>("#total");


    if (subTotal && total) {

        subTotal.innerHTML = "";
        total.innerHTML = "";


        //Subtotal
        const precio = document.createElement("p");
        const p = document.createElement("p");

        p.textContent = "Subtotal";
        precio.textContent = "$ " + calcularTotal().toString();

        subTotal.appendChild(p);
        subTotal.appendChild(precio);

        // Total
        const precioTotal = document.createElement("p");
        const t = document.createElement("p");

        precioTotal.textContent = "$ " + calcularTotal().toString();
        precioTotal.style.fontWeight = "600";

        t.textContent = "Total";
        t.style.fontWeight = "600";
        t.style.fontSize = "large";

        total.appendChild(t);
        total.appendChild(precioTotal);

    }
}

mostrarTotal();

//Eventos de Botones Total

const btnVaciarCarrito = document.querySelector<HTMLButtonElement>("#btn-vaciar");

btnVaciarCarrito?.addEventListener("click", (Event: MouseEvent) => {
    //Vaciamos el carrito
    productoCarrito.length = 0;

    //Persistimos el carrito
    localStorage.setItem("carrito", JSON.stringify(productoCarrito));
    mostrarCarrito();
    mostrarTotal();

})

const btnFinalizar = document.querySelector<HTMLButtonElement>("#btn-finalizar");

btnFinalizar?.addEventListener("click", (Event: MouseEvent) => {

    const total = document.querySelector<HTMLElement>("#total-box");

    const confirmacion = document.createElement("div");
    confirmacion.classList.add("confirmacion");

    const mensaje = document.createElement("p");

    mensaje.textContent = "Compra comfirmado!";
    confirmacion.appendChild(mensaje);


    if (total) {
        confirmacion.style.display = "block"
        total.appendChild(confirmacion);

        setTimeout(() => {
            confirmacion.style.display = "none";
        }, 2000);
        setTimeout
    }


})