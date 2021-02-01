import React, { useState, useEffect } from 'react';
import Carousel, { Modal, ModalGateway } from 'react-images';

const ImagesView = (props) => {
  const { images = [], open, onClose } = props;

  const shows: { source: string; }[] = []

  images.forEach(o=>{
    shows.push({
      source:o
    })
  })
  const [modalIsOpen, setModalIsOpen] = useState(open)
  useEffect(() => {
    setModalIsOpen(open)
  }, [images, open])

  const toggleModal = () => {
    setModalIsOpen(!modalIsOpen)
    if (typeof onClose === 'function') {
      onClose()
    }

  }
  const styleInit = {
    header: (base, state) => ({ // 头部样式
      position: 'absolute',
      top: 90,
      right: 90,
      zIndex: 9999,
    }),
    view: (base, state) => ({
      textAlign: 'center',
      height: state.isFullscreen?'100%':600  // 当点击全屏的时候图片样式
    })
  }
  return (
    <ModalGateway >
      {modalIsOpen ? (
        <Modal onClose={toggleModal} >
          <Carousel views={shows} styles={styleInit}/>
        </Modal>
      ) : null}
    </ModalGateway>
  );

}


export default ImagesView